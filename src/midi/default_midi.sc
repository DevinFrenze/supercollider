(
  var nodes, velocities, onFunc, offFunc, pitchBendFunc, volumeFunc,
    calcAmp, calcFreq, setPitchBend, setVolume;

  ~ampScale = 0.00315;
  ~volume = 1;
  ~pitchBend = 1;
  ~pitchBendRange = 0.5;

  q != nil && q.(); // clear midi note on and off if assigned
  nodes = Array.newClear(129);       // array has one slot per possible MIDI note
  velocities = Array.newClear(129);  // array has one slot per possible MIDI note

  setPitchBend = { |val|
    var lowerLimit = 1 - ~pitchBendRange,
        range = (2 * ~pitchBendRange * (val / 16384));
    ~pitchBend = lowerLimit + range;
  };
  setVolume = { |val|       ~volume = val / 128; }; calcFreq =  { |note|      note.midicps * ~pitchBend };
  calcAmp  =  { |velocity|  velocity * ~ampScale * ~volume };

  onFunc = MIDIFunc.noteOn({ |velocity, note, chan, src|
    nodes[note] = Synth(~defaultSynthName, [
      \freq, calcFreq.(note),
      \amp, calcAmp.(velocity)
    ]);
    velocities[note] = velocity;
  });

  offFunc = MIDIFunc.noteOff({ |val, note, chan, src|
    nodes[note].release;
    nodes[note] = nil;
    velocities[note] = nil;
  });

  pitchBendFunc = MIDIFunc.bend({ |val, chan, src|
    setPitchBend.(val);
    nodes.do({ | synth, index |
      if ((synth != nil),
        { synth.set(\freq, calcFreq.(index)) }
      );
    });
  });

  volumeFunc = MIDIFunc.cc({ |val, chan, src|
    setVolume.(val);
    nodes.do({ | synth, index |
      if ( (synth != nil) && (velocities[index] != nil),
        { synth.set(\amp, calcAmp.(velocities[index])) }
      );
    });
  }, 1);

  q = { onFunc.free; pitchBendFunc.free; volumeFunc.free; offFunc.free; };
)

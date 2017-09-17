(
  var nodes, velocities, onFunc, offFunc, pitchBendFunc, volumeFunc, knobFuncs,
    calcAmp, calcFreq, setPitchBend, setVolume,
    ampScale, volume, pitchBend, pitchBendRange;

  postln("start loading simple midi");

  knobFuncs = [];
  ampScale = 0.00315;
  volume = 1;
  pitchBend = 1;
  pitchBendRange = 0.5;

  ~midiFreeFunc != nil && ~midiFreeFunc.();
  nodes = Array.newClear(129);       // array has one slot per possible MIDI note
  velocities = Array.newClear(129);  // array has one slot per possible MIDI note

  setPitchBend = { |val|
    var lowerLimit = 1 - pitchBendRange,
        range = (2 * pitchBendRange * (val / 16384));
    pitchBend = lowerLimit + range;
  };
  setVolume = { |val| volume = val / 128; };
  calcFreq =  { |note|      note.midicps * pitchBend };
  calcAmp  =  { |velocity|  velocity * ampScale * volume };

  onFunc = MIDIFunc.noteOn({ |velocity, note, chan, src|
    var eventArray, args;

    postln(~current_event);
    eventArray = ~current_event.asPairs;
    args = eventArray ++ [
      \freq, calcFreq.(note),
      \amp, calcAmp.(velocity)
    ];

    nodes[note] = Synth(~current_synth, args);
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

  ~current_midi_controls.do { |association, i|
    knobFuncs = knobFuncs.add(MIDIFunc.cc({ |val, chan, src|
      var param = association.key,
          func = association.value;
          
      postln("" ++ param + func.(val));
      ~current_event[param] = func.(val);
      nodes.do({ | synth, index |
        if ((synth != nil),
          { synth.set(param, func.(val)) }
        );
      });
    }, i + 20));
  };

  ~midiFreeFunc = {
    onFunc.free;
    pitchBendFunc.free;
    volumeFunc.free;
    offFunc.free;
    knobFuncs.do({|func| func.free;});
  };

  postln("done loading simple midi");
)

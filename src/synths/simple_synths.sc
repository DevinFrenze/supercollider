(
  var name = "simple", synth_gen, midi_controls, event;

  postln("start loading" + name + "synths");

  midi_controls = ~midi_controls.at(\amp_adsr) ++ ~midi_controls.at(\filter_freq_adsr) ++ ~midi_controls.at(\rlpf);

  event = ~events.at(\amp_adsr) ++ ~events.at(\filter_freq_adsr) ++ ~events.at(\rlpf);

  synth_gen = { |carrier = \saw |
    var synth_name = name ++ "_" ++ carrier,
        carrier_event = ~events.at(carrier),
        carrier_midi_controls = ~midi_controls.at(carrier),
        synth_event = event.copy,
        synth_midi_controls = midi_controls.copy;

    if (carrier_event != nil, { synth_event = synth_event ++ carrier_event; });

    if (carrier_midi_controls != nil, { synth_midi_controls = synth_midi_controls ++ carrier_midi_controls; });

    ~events.put(synth_name, synth_event);
    ~midi_controls.put(synth_name, synth_midi_controls);

    SynthDef(\simple_ ++ carrier, { | gate = 1, freq = 440 |
      var frequency = freq,
          signal = SynthDef.wrap(~carriers.at(carrier), [], [frequency]),
          env = EnvGen.ar(SynthDef.wrap(~amp_adsr), gate, doneAction: 2),
          filter_freq_env = EnvGen.ar(SynthDef.wrap(~filter_freq_adsr, [], [freq]), gate);
      signal = signal * env;
      signal = SynthDef.wrap(~rlpf, [], [signal, filter_freq_env]);
      SynthDef.wrap(~stereo, [], [signal]);
    }).add;

    postln("made synth def" + synth_name);
  };

  ~carriers.keys.do { |key| synth_gen.(key); };

  postln("done loading" + name + "synths");
)

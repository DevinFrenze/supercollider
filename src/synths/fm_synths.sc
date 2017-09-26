(
  var name = "fm", synth_gen, midi_controls, event, add_event, add_midi_controls;

  postln("start loading" + name + "synths");

  midi_controls =
    ~midi_controls.at(\amp_adsr) ++
    ~midi_controls.at(\filter_freq_adsr) ++
    ~midi_controls.at(\rlpf) ++
    ~midi_controls.at(\fm);

  event =
    ~events.at(\amp_adsr) ++
    ~events.at(\filter_freq_adsr) ++
    ~events.at(\rlpf) ++
    ~events.at(\fm);

  add_event = { |synth_name, carrier, modulator|
    var carrier_event = ~events.at(\carriers).at(carrier),
        modulator_event = ~events.at(\modulators).at(modulator),
        new_event = event.copy ++ carrier_event ++ modulator_event;
    ~events.put(synth_name, new_event);
  };

  add_midi_controls = { |synth_name, carrier, modulator|
    var carrier_controls = ~midi_controls.at(\carriers).at(carrier),
        modulator_controls = ~midi_controls.at(\modulators).at(modulator),
        new_controls = midi_controls.copy ++ carrier_controls ++ modulator_controls;
    ~midi_controls.put(synth_name, new_controls);
  };

  synth_gen = { |carrier, modulator, fm_layers|
    var synth_name = name ++ "_" ++ carrier ++ "_by_" ++ modulator ++ "_" ++ fm_layers ++ "_times",
        fm_func = ~fm_gen.(fm_layers, modulator);

    add_event.(synth_name, carrier, modulator);
    add_midi_controls.(synth_name, carrier, modulator);

    SynthDef(synth_name, { | gate = 1, freq = 440 |
      var frequency = SynthDef.wrap(fm_func, [], [freq]),
          signal = SynthDef.wrap(~carriers.at(carrier), [], [frequency]),
          env = EnvGen.ar(SynthDef.wrap(~amp_adsr), gate, doneAction: 2),
          filter_freq_env = EnvGen.ar(SynthDef.wrap(~filter_freq_adsr, [], [freq]), gate);
      signal = signal * env;
      signal = SynthDef.wrap(~rlpf, [], [signal, filter_freq_env]);
      SynthDef.wrap(~stereo, [], [signal]);
    }).add;

    postln("made synth def" + synth_name);
  };

  ~carriers.keys.do { |carrier|
    4.do { |layers|
      synth_gen.(carrier, \sine, (layers + 1));
    };
  };

  postln("done loading" + name + "synths");
)

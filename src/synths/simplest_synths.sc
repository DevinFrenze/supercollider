(
  var name = "simplest", synth_gen, midi_controls, event;

  postln("start loading" + name + "synths");

  midi_controls = [
    // envelope
    \attack -> ~linear_interp_gen.(),
    \decay -> ~linear_interp_gen.(),
    \sustain -> ~linear_interp_gen.(),
    \release -> ~linear_interp_gen.(),
    // filter params
    \rq -> ~exponential_interp_gen.(),
    \filter_freq -> ~exponential_interp_gen.(20, 20000)
  ];

  event = (
    // env
    attack: 0,
    decay: 0,
    sustain: 1,
    release: 0,
    // filter params
    rq: 1,
    filter_freq: 20000
  );

  synth_gen = { |carrier = \saw |
    var synth_name = name ++ "_" ++ carrier,
        synth_event = event.copy,
        synth_midi_controls = midi_controls.copy;

    switch (carrier,
      \sine, {
        synth_midi_controls.add(\phase -> ~linear_interp_gen.(0, 2pi));
        synth_event.put(\phase, 0);
      },
      \pulse, {
        synth_midi_controls.add(\width -> ~linear_interp_gen.());
        synth_event.put(\width, 0.5);
      },
      \blip, {
        synth_midi_controls.add(\num_harm -> ~exponential_interp_gen.(1, 200));
        synth_event.put(\numHarmonics, 200);
      }
    );

    ~events.put(synth_name, synth_event);
    ~midi_controls.put(synth_name, synth_midi_controls);

    SynthDef(synth_name, { | gate = 1, freq = 440 |
      var frequency = freq,
          signal = SynthDef.wrap(~carriers.at(carrier), [], [frequency]),
          env = EnvGen.ar(SynthDef.wrap(~amp_adsr), gate, doneAction: 2);
      signal = signal * env;
      signal = SynthDef.wrap(~rlpf, [], [signal]);
      SynthDef.wrap(~stereo, [], [signal]);
    }).add;

    postln("made synth def" + synth_name);
  };

  ~carriers.keys.do { |key| synth_gen.(key); };

  postln("done loading" + name + "synths");
)

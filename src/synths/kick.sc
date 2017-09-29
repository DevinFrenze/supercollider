(
  var name = "kick", synth_gen, midi_controls, event;

  postln("start loading" + name + "synths");

  midi_controls = ~midi_controls.at(\amp_perc) ++ ~midi_controls.at(\kick_freq_env);
  event = ~events.at(\amp_perc) ++ ~events.at(\kick_freq_env);

  ~events.put(name, event);
  ~midi_controls.put(name, midi_controls);

  synth_gen = {
    SynthDef(name, { | freq = 440 |
      var frequency = SynthDef.wrap(~kick_freq_env, [], [freq]),
          signal = SynthDef.wrap(~carriers.at(\sine), [], [frequency]),
          env = EnvGen.ar(SynthDef.wrap(~amp_perc), 1, doneAction: 2);
      signal = signal * env;
      SynthDef.wrap(~stereo, [], [signal]);
    }).add;

    postln("made synth def" + name);
  };

  synth_gen.();

  postln("done loading" + name + "synths");
)

(
  ~scale_frequency = { | min, max, degree |
    var power_of_two = (1 / degree) - 1,
        range = max - min;
    min + (range / 2.pow(power_of_two));
  };

  ~carriers = Dictionary[
    \sine   -> {| freq = 440, phase = 0 | SinOsc.ar(freq, phase) },
    \pulse  -> {| freq = 400, width = 0.5 | Pulse.ar(freq, width) },
    \saw    -> {| freq = 440 | Saw.ar(freq) },
    \blip   -> {| freq = 400, numHarmonics = 200 | Blip.ar(freq, numHarmonics) }
  ];

  ~rlpf = {| in, filter_freq = 440, rq = 1 | RLPF.ar(in, filter_freq.clip(0, 20000), rq) };

  ~amp_adsr = {| attack = 0.0, decay = 0.0, sustain = 1, release = 0.1, curve = 'lin' |
    Env.new([0, 1, sustain, 0], [attack, decay, release], curve, 2);
  };

  ~filter_adsr = {| freq = 440,
    filter_attack = 0.02, filter_decay = 0.02, filter_sustain = 0.5, filter_release = 0.1,
    filter_peak = 1, filter_curve = 'lin' |
    var peak = ~scale_frequency.(freq, 20000, filter_peak),
      sustain= ~scale_frequency.(freq, peak, filter_sustain);
    Env.new([freq, peak, sustain, freq], [filter_attack, filter_decay, filter_release], filter_curve, 2);
  };

  ~stereo = { | signal, out = 0, pan = 0, amp = 1 | Out.ar(out, Pan2.ar(signal, pan, amp)) };

  ~simple_synth_gen = { |carrier = \saw |
    SynthDef(\simple_ ++ carrier, { | gate = 1, freq = 440 |
      var frequency = freq,
          signal = SynthDef.wrap(~carriers.at(carrier), [], [frequency]),
          env = EnvGen.ar(SynthDef.wrap(~amp_adsr), gate, doneAction: 2),
          filter_env = EnvGen.ar(SynthDef.wrap(~filter_adsr, [], [freq]), gate);
      signal = SynthDef.wrap(~rlpf, [], [signal * env, filter_env]);
      SynthDef.wrap(~stereo, [], [signal]);
    }).add;
  };

  ~simple_synth_gen.(\pulse); 
  ~simple_synth_gen.(\saw); 
  ~simple_synth_gen.(\blip); 
)

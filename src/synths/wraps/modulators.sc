(
  var modulators_events, modulators_midi_controls;

  ~modulators = Dictionary[
    \sine  -> {| freq, range, offset, modulator_phase = 0 |
      SinOsc.ar(freq, modulator_phase, range, offset);
    },
    \pulse -> {| freq, range, offset, modulator_width = 0.5 |
      LFPulse.ar(freq, 0, modulator_width, range, offset);
    },
    \saw   -> {| freq, range, offset |
      LFSaw.ar(freq, 0, range, offset);
    },
    \blip  -> {| freq, range, offset, modulator_num_harmonics = 200 |
      Blip.ar(freq, modulator_num_harmonics, range, offset);
    }
  ];

  modulators_events = Dictionary.new;
  modulators_events.put(\sine,  ( \modulator_phase: 0 ));
  modulators_events.put(\saw,   ( ));
  modulators_events.put(\pulse, ( \modulator_width: 0.5 ));
  modulators_events.put(\blip,  ( \modulator_num_harmonics: 200 ));
  ~events.put(\modulators, modulators_events);

  modulators_midi_controls = Dictionary.new;
  modulators_midi_controls.put(\sine,  [ \modulator_phase -> ~linear_interp_gen.(0, 2pi) ]);
  modulators_midi_controls.put(\saw,   [ ]);
  modulators_midi_controls.put(\pulse, [ \modulator_width -> ~linear_interp_gen.() ]);
  modulators_midi_controls.put(\blip,  [ \modulator_num_harmonics -> ~exponential_interp_gen.(1, 200) ]);
  ~midi_controls.put(\modulators, modulators_midi_controls);
)

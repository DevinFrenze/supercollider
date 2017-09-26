(
  var ratios = Array.fill(6, { |index| 2 ** (index - 2); });

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

  // fm_ratio is the ratio between the carrier and the modulator
  // beta is the amount the carrier deviates from its unmodulated frequency divided by the modulator frequency

  ~fm_gen = {
    | fm_layers, modulator |

    { | freq = 440, fm_ratio = 1, fm_beta = 1|
      var mod = freq;

      fm_layers.do {
        mod = (~modulators.at(modulator)).(
          mod,
          mod * fm_ratio * fm_beta,
          mod * fm_ratio
        );
      };

      mod;
    };
  }; 

  // TODO need an interp for just whole numbers ! (for fm_ratio)
  ~midi_controls.put(\fm, [
    \fm_ratio -> {
      | val | 
      var num = (ratios.size * val / 128).floor;
      ratios[(ratios.size * val / 128).floor];
    },
    \fm_beta -> ~exponential_interp_gen.(0.01, 4)
  ]);

  ~events.put(\fm, (
    \fm_ratio: 1,
    \fm_beta: 1
  ));
)

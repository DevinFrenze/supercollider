(
  ~filter_freq_adsr = {
    | freq = 440,
    filter_attack = 0.02, filter_decay = 0.02, filter_sustain = 20000, filter_release = 0.1,
    filter_freq = 20000, filter_curve = 'lin' |

    var envelopeLevels = [freq, filter_freq, filter_sustain, freq],
      envelopeTimes = [filter_attack, filter_decay, filter_release];

    Env.new(envelopeLevels, envelopeTimes, filter_curve, 2);
  };

  ~midi_controls.put(\filter_freq_adsr, [
    \filter_attack -> ~exponential_interp_gen.(),
    \filter_decay -> ~exponential_interp_gen.(),
    \filter_sustain -> ~exponential_interp_gen.(20, 20000),
    \filter_release -> ~exponential_interp_gen.(0.01, 2),
  ]);

  ~events.put(\filter_freq_adsr, (
    filter_attack: 0,
    filter_decay: 0,
    filter_sustain: 20000,
    filter_release: 0
  ));
)

(
  ~amp_adsr = {
    | attack = 0.0, decay = 0.0, sustain = 1, release = 0.1, curve = 'lin' |
    var envelopeLevels = [0, 1, sustain, 0],
        envelopeTimes = [attack, decay, release];
    Env.new(envelopeLevels, envelopeTimes, curve, 2);
  };

  ~events.put(\amp_adsr, (
    attack: 0,
    decay: 0,
    sustain: 1,
    release: 0
  ));

  ~midi_controls.put(\amp_adsr, [
    \attack -> ~exponential_interp_gen.(),
    \decay -> ~exponential_interp_gen.(),
    \sustain -> ~linear_interp_gen.(),
    \release -> ~exponential_interp_gen.(0.01, 2)
  ]);
)

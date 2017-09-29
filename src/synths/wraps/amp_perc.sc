(
  ~amp_perc = {
    | attack = 0.01, release = 0.1, curve = 'lin' |
    Env.perc(attack, release, curve: curve);
  };

  ~events.put(\amp_perc, (
    attack: 0.01,
    release: 0.3
  ));

  ~midi_controls.put(\amp_perc, [
    \attack -> ~exponential_interp_gen.(0.01, 0.1),
    \release -> ~exponential_interp_gen.(0.01, 3)
  ]);
)

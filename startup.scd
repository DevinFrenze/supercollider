Server.local.boot;
Server.local.makeWindow;
MIDIClient.init;
MIDIIn.connectAll;

~events = Dictionary.new;
~midi_controls = Dictionary.new;

this.executeFile(Platform.userConfigDir +/+ 'src/util.sc');
this.executeFile(Platform.userConfigDir +/+ 'src/synths/index.sc');

// ~load_synth.("simplest_pulse");
// ~load_synth.("simple_pulse");
// ~load_synth.("simple_saw");
~load_synth.("fm_sine_by_sine_2_times");
// ~load_synth.("kick");

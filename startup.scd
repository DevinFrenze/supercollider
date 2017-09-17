Server.local.boot;
Server.local.makeWindow;
MIDIClient.init;
MIDIIn.connectAll;
this.executeFile(Platform.userConfigDir +/+ 'src/util.sc');
this.executeFile(Platform.userConfigDir +/+ 'src/synths/index.sc');
~load_synth.("simple_pulse");

# using scvim
* `:SClangStart` to launch sclang
* `:SClangKill` to kill sclang
* `:SCTags` to generate sclang tags
* `Fn + 6` to execute line
* `Fn + 5` to execute block
* `Fn + 12` to free all synths

# using supercollider server
* `s.boot` boot the default local server
* `s.quit` kill the default local server

# startup
* make an alias from `Platform.userConfigDir +/+ startup.scd`
* make an alias from `Platform.userConfigDir +/+ src/`
* it will run execute when you start sclang

# TODO
* use keyboard to control event params in a pattern
* page through available controls so that there can be more controls than knobs
* smooth out transitions between knobs and params (slide into new value)
* add percussion synths
* figure out why it takes two attempts for a clean start

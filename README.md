Dactyl Control
==============

This is a very simple minecraft mod for Forge that calls [dactyl-remote-control](https://github.com/Kasama/dactyl-remote-control) to change layers in my [personal keyboard](https://github.com/Kasama/qmk_firmware/tree/master/keyboards/kasama_dactyl/v1) between the base and game layers.

Whenever an UI screen in minecraft is opened, it changes to the base layer `0` and when it's closed and I'm back in game, it changes to the game layer `4`.

If it ever runs out of sync, it can be reset with the `/dactylctl reset` command.

This mod works client-side only. No support needed on the server.

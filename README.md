# My Work Status Phones

I have two old Android phones that I'm using in my house to display my work status, which is merely a web page served by a somewhat simple Node app.

On the phones, the web page is displayed by the Android app "Fully Kiosk Browser" (FKB). However, Android kills the app every couple of days to upgrade the webview component, and this cannot be prevented. My workaround was to
- Root the phone
- Install Termux and cronie
- Schedule a cron job to run a bash script that starts FKB if it is not running.
- Use Termux to start FKB when I log into the phone

This repo contains the scripts I use on these phones.
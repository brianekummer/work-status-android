#!/data/data/com.termux/files/usr/bin/sh

FKB_PACKAGE_NAME="de.ozerov.fully"
FKB_ACTIVITY_COMPONENT="${FKB_PACKAGE_NAME}/.FullyActivity"
FKB_INTENT_FLAGS="0x10000000"
LOG_FILE=~/ensure_fkb_running.log

log_message() {
  echo "$(date '+%Y-%m-%d %H:%M:%S') - $1" >> "$LOG_FILE"
}

# Check if FKB's UI is focused or visible using your validated grep pattern
FKB_IS_VISIBLE_OUTPUT=$(su -c "dumpsys activity activities | grep -E 'ResumedActivity|FocusedActivity' | grep '$FKB_PACKAGE_NAME'")
if [ -z "$FKB_IS_VISIBLE_OUTPUT" ]; then
  # FKB UI is NOT visible/focused
  # AGGRESSIVE APPROACH: We will attempt to start it, regardless of other FKB background processes.
  log_message "FKB UI not detected as visible/focused via dumpsys. Attempting to start FKB..."

  if su -c "am start -n '$FKB_ACTIVITY_COMPONENT' -f '$FKB_INTENT_FLAGS'" >> "$LOG_FILE" 2>&1; then
    log_message "SUCCESS: 'am start' command for FKB executed (UI was not visible)."
  else
    AM_START_EXIT_CODE=$?
    log_message "ERROR: 'am start' command for FKB failed with exit code $AM_START_EXIT_CODE (UI was not visible)."
  fi
fi
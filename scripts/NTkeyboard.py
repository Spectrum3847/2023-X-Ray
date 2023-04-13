import time
import threading
from networktables import NetworkTables
import keyboard
import winsound

# Set the IP address of the networktables server and the keys to monitor
server_ip = "10.38.47.2"
keys_to_monitor = {
    "RecordMatch": "F9",
    "StopRecording": "F10"
}
sound_to_play = "Beep"

# Connect to the networktables server
nt = NetworkTables.getTable("SmartDashboard")
NetworkTables.initialize(server=server_ip)

# Define a function to monitor the networktables values
def monitor_values():
    # Create a dictionary to store the current values of the networktables keys
    current_values = {}
    for key, value in keys_to_monitor.items():
        current_values[key] = nt.getBoolean(key, False)

    # Keep monitoring the values in a loop
    while True:
        # Create a dictionary to store the updated values of the networktables keys
        new_values = {}
        for key, value in keys_to_monitor.items():
            new_values[key] = nt.getBoolean(key, False)
        # Check each key to see if its value has changed from false to true
        for key, value in keys_to_monitor.items():
            # If the value has changed from false to true, send a keyboard press
            if current_values[key] != new_values[key]:
                current_values[key] = new_values[key]
                print("Value changed")
                if new_values[key]:
                    keyboard.press(value)
                    time.sleep(0.1)
                    keyboard.release(value)
                    winsound.PlaySound(sound_to_play, winsound.SND_ALIAS)

        # Wait for a short time before checking the values again
        time.sleep(0.1)

# Start the monitoring thread
monitoring_thread = threading.Thread(target=monitor_values)
monitoring_thread.start()
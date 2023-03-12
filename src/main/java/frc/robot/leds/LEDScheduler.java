// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.leds;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.RobotTelemetry;
import frc.robot.leds.commands.ChaseLEDCommand;
import frc.robot.leds.commands.LEDCommandBase;
import frc.robot.leds.commands.LEDCommandBase.Priority;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** Add your docs here. */
// TODO: add CRITICAL commands
public class LEDScheduler {
    ArrayList<Animation> animationArrary = new ArrayList<Animation>();
    Animation top;
    Animation defaultAnimation;
    int firstFreeIndex;
    boolean firstCommand = false;

    public LEDScheduler(LEDs subsystem) {
        RobotTelemetry.print("Starting LED Scheduler Thread");
        LEDSchedulerThread.start();
    }

    public void setDefaultAnimation(String name, LEDCommandBase command) {
        defaultAnimation = new Animation(name, command, 1, -101);
        if (top == null) {
            top = defaultAnimation;
        }
        addAnimation(defaultAnimation);
    }

    private void intialAnimation() {
        setDefaultAnimation(
                "Default LED Animation",
                new ChaseLEDCommand("LED Default", Priority.DEFAULT, -101, 1));
    }

    private void runScheduler() {
        if (top == null) {
            intialAnimation();
        }
        // It the top animation isn't scheduled, schedule it. EDIT: I know why this exists but
        // it breaks the ending function of commands
        // if (!top.getCommand().isScheduled()) {
        //     top.getCommand().schedule();
        // }

        // Increment through all the Animations
        for (int j = 0; j < animationArrary.size(); j++) {
            Animation animation = animationArrary.get(j);

            // Delete itemss with a timeout less than 0 and greater than -100
            // Start things at -100 if you want them to never timeout
            if (animation.getTimeout() < 0 && animation.getTimeout() > -100) {
                // found, delete.
                animationArrary.remove(j);
            }
            animation.decrementTimeOut();
        }
        // Sort the list by priority
        Collections.sort(animationArrary, new SortbyPriority());
        for (Animation animation : animationArrary) {
            System.out.print(animation.getCommand().getName() + ", ");
        }
        System.out.println();

        /* If we have a new top that isn't a default animation as that it is scheduled differently */
        if (top.getName() != animationArrary.get(0).getName()
                && animationArrary.get(0).getPriority() != 1) {
            // top.getCommand().cancel(); //ending commands should be handled by the
            // CommandScheduler
            top = animationArrary.get(0);
            top.getCommand().schedule();
            top.getCommand().ledInitialize();
            if (Robot.isSimulation()) {
                RobotTelemetry.print("LEDs Set To: " + top.getName());
            }
        }

        /* Execute the highest priority interrupting/base animation if there is one */
        int firstBaseAnimationIndex = getFirstBaseAnimationIndex();
        if (firstBaseAnimationIndex != -1) {
            animationArrary.get(firstBaseAnimationIndex).getCommand().ledExecute();
        }

        /* Execute the parallel commands by going through the arraylist backwards so
        they overlay base animations while also competing with other parallel commands
        that were executed earlier in runtime */
        // for (int i = animationArrary.size() - 1; i >= 0; i--) {
        //     Animation animation = animationArrary.get(i);
        //     if (animation.getCommand().isParallel()) {
        //         animation.getCommand().ledExecute();
        //     }
        // }
        for (Animation animation : animationArrary) {
            if (animation.getCommand().isParallel()) {
                animation.getCommand().ledExecute();
                break;
            }
        }
        firstCommand = false;
    }

    private int getFirstBaseAnimationIndex() {
        for (int i = 0; i < animationArrary.size(); i++) {
            if (!animationArrary.get(i).getCommand().isParallel()) {
                return i;
            }
        }
        return -1;
    }

    public void addAnimation(String name, LEDCommandBase command, int priority, double seconds) {
        Animation animation = new Animation(name, command, priority, seconds * 5);
        addAnimation(animation);
    }

    private void addAnimation(Animation animation) {
        // Check if this animation is already added (same name), if it is delete the old
        // one and add the new one
        if (animationArrary.indexOf(animation) >= 0) {
            animationArrary.remove(animationArrary.indexOf(animation));
        }
        animation.getCommand().setName(animation.name);
        // Ensure that this animation takes priority
        decrementPriorities();
        animationArrary.add(animation);
    }

    /**
     * Decrements every animation priority currently in queue by one to make the latest executed
     * animation have priority. Ignores the default animation because it's already at 1.
     */
    private void decrementPriorities() {
        for (int i = 0; i < animationArrary.size(); i++) {
            Animation animation = animationArrary.get(i);
            if (animation.getPriority() > 1) {
                animation.decrementPriority();
            }
        }
    }

    public void removeAnimation(String name) {
        for (int i = 0; i < animationArrary.size(); i++) {
            if (animationArrary.get(i).getName() == name) {
                animationArrary.remove(i);
            }
        }
    }

    class Animation {
        String name;
        LEDCommandBase command;
        int priority;
        double timeout;

        public Animation(String name, LEDCommandBase command, int priority, double timeout) {
            this.name = name;
            this.command = command;
            this.priority = priority;
            this.timeout = timeout;
        }

        public boolean equals(Object object) {
            boolean isEqual = false;

            if (object != null && object instanceof Animation) {
                isEqual = (this.name == ((Animation) object).getName());
            }

            return isEqual;
        }

        public String getName() {
            return name;
        }

        public LEDCommandBase getCommand() {
            return command;
        }

        public int getPriority() {
            return this.priority;
        }

        public void setPriority(int p) {
            priority = p;
        }

        public double getTimeout() {
            return this.timeout;
        }

        public void setTimeout(double t) {
            timeout = t;
        }

        public void decrementTimeOut() {
            timeout -= 1;
        }

        public void decrementPriority() {
            priority -= 1;
        }
    }

    class SortbyPriority implements Comparator<Animation> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Animation a, Animation b) {
            return b.priority - a.priority;
        }
    }

    Thread LEDSchedulerThread =
            new Thread(
                    new Runnable() {
                        public void run() {
                            while (true) {
                                runScheduler();
                                Timer.delay(0.02); // Loop runs at 50hz
                            }
                        }
                    });
}

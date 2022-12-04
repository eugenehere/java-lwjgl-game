package lwjgl.gradle.lambda;

public class ExitGameLambda implements Lambda {
    @Override
    public void execute() {
        System.out.println("Exiting...");
        System.exit(0);
    }
}

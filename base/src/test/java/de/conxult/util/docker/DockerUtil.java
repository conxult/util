package de.conxult.util.docker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joerg
 */
@Disabled
public class DockerUtil {

  String       command;
  String       container;
  String       name;
  List<String> arguments = new ArrayList<>();

  private DockerUtil(String command) {
    this.command = command;
    arguments("docker", command);
  }

  public static DockerUtil run() {
    return new DockerUtil("run");
  }

  public static Process kill(String name)
    throws IOException {
    return new DockerUtil("kill").arguments(name).execute();
  }

  public DockerUtil name(String name) {
    this.name = name;
    return arguments("--name", name);
  }

  public DockerUtil publish(String publish) {
    return arguments("-p", publish);
  }

  public DockerUtil environment(String name, String value) {
    return arguments("-e", name + "=" + value);
  }

  public DockerUtil autoRemove() {
    return arguments("--rm=true");
  }

  public DockerUtil arguments(String... arguments) {
    for (String argument : arguments) {
      this.arguments.add(argument);
    }
    return this;
  }

  public Process container(String container)
    throws IOException {
    return arguments(container).execute();
  }

  Process execute()
    throws IOException {
    System.out.println(String.join(" ", arguments));
    Process process = Runtime.getRuntime().exec(arguments.toArray(new String[arguments.size()]));
    new Logger(process.getInputStream(), System.out);
    new Logger(process.getErrorStream(), System.err);
    return process;
  }

  private static class Logger extends Thread {
    BufferedReader reader;
    PrintStream    writer;

    public Logger(InputStream inputStream, PrintStream outputStream)
      throws IOException {
      reader = new BufferedReader(new InputStreamReader(inputStream));
      writer = outputStream;
      start();
    }

    @Override
    public void run() {
      try {
        for (String line = reader.readLine(); (line != null); line = reader.readLine()) {
          writer.println(line);
        }
      } catch (IOException any) {
        any.printStackTrace();
      }
    }

  }


}

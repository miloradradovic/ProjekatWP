package rest;

import com.google.gson.Gson;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;

public class SparkMain {
	
	private static Gson g = new Gson();

	public static void main(String[] args) throws IOException {
		
		port(80);
		staticFiles.externalLocation(new File("."+File.separator+"static"+File.separator+"html").getCanonicalPath());
		
		

	}

}

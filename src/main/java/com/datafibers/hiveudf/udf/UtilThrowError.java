package com.datafibers.hiveudf.udf;

import java.util.Random;
 
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public final class UtilThrowError extends UDF {
  private static Random rand = new Random();

  public Text evaluate(final Text s) throws Exception {
    if (rand.nextInt(100) < 10) {
      throw new Error("FAKE_ERROR: " + s.toString());
    }
    return new Text("SKIP");
  }
}

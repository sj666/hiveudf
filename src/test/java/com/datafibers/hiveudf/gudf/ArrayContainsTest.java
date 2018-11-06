package com.datafibers.hiveudf.gudf;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaBooleanObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class ArrayContainsTest {


    @Test
    public void testComplexUDFReturnsCorrectValues() throws HiveException {

        // set up the models we need
        ArrayContains arrayContains = new ArrayContains();
        ObjectInspector stringOI = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        ObjectInspector listOI = ObjectInspectorFactory.getStandardListObjectInspector(stringOI);
        JavaBooleanObjectInspector resultInspector = (JavaBooleanObjectInspector) arrayContains.initialize(new ObjectInspector[]{listOI, stringOI});

        // create the actual UDF arguments
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        // test our results

        // the value exists
        Object result = arrayContains.evaluate(new DeferredObject[]{new DeferredJavaObject(list), new DeferredJavaObject("a")});
        Assert.assertEquals(true, resultInspector.get(result));

        // the value doesn't exist
        Object result2 = arrayContains.evaluate(new DeferredObject[]{new DeferredJavaObject(list), new DeferredJavaObject("d")});
        Assert.assertEquals(false, resultInspector.get(result2));

        // arguments are null
        Object result3 = arrayContains.evaluate(new DeferredObject[]{new DeferredJavaObject(null), new DeferredJavaObject(null)});
        Assert.assertNull(result3);
    }
}
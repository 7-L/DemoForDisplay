package com.example.demo.controller;


import it.uniroma1.dis.wsngroup.gexf4j.core.EdgeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.Gexf;
import it.uniroma1.dis.wsngroup.gexf4j.core.Graph;
import it.uniroma1.dis.wsngroup.gexf4j.core.Mode;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.Attribute;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;


public class gexfUtils {


    public static String generate(String json) {
        if (json != null) {
            Gexf gexf = new GexfImpl();
            Calendar date = Calendar.getInstance();
            gexf.getMetadata()
                    .setLastModified(date.getTime())
                    .setCreator("hzx")
                    .setDescription("A node network");
            gexf.setVisualization(true);
            Graph graph = gexf.getGraph();
            graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);
            AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
            graph.getAttributeLists().add(attrList);
            Attribute attUrl = attrList.createAttribute("class", AttributeType.INTEGER, "Class");
            Attribute attIndegree = attrList.createAttribute("pageranks", AttributeType.DOUBLE, "PageRank");

            /*解析json ，分析一共几个节点*/
            JSONObject jsonObject = new JSONObject(json);
//            JSONObject jsonObj = json.getJSONObject("data");//获取json数组中的data项  Object为最外一层，故注释掉  
            JSONArray paths = jsonObject.getJSONArray("data");

            for (int i = 0; i < paths.length(); i++) {
                int id = paths.getJSONObject(i).getInt("id");
                String content = paths.getJSONObject(i).getString("content");
                String messageId = paths.getJSONObject(i).getString("messageId");
                Node node = graph.createNode(String.valueOf(id + 1));
                node.setLabel("节点" + id).getAttributeValues().addValue(attUrl, content).addValue(attIndegree, "0.14658");

            }
            for (int i = 0; i < paths.length(); i++) {

            }
            Node gephi = graph.createNode("0");
            gephi
                    .setLabel("郝大通")
                    .getAttributeValues()
                    .addValue(attUrl, "3")
                    .addValue(attIndegree, "0.14658");


            Node webatlas = graph.createNode("1");
            webatlas
                    .setLabel("郝大通")
                    .getAttributeValues()
                    .addValue(attUrl, "3")
                    .addValue(attIndegree, "0.14658");

            gephi.connectTo("0", webatlas).setWeight(0.8f);

            StaxGraphWriter graphWriter = new StaxGraphWriter();
            File f = new File("/src/main/java/resources/static/test.gexf");
            Writer out;
            try {
                out = new FileWriter(f, false);
                graphWriter.writeToStream(gexf, out, "UTF-8");
                System.out.println(f.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "ok";
    }


    public static void main(String[] args) {
        Gexf gexf = new GexfImpl();
        Calendar date = Calendar.getInstance();

        gexf.getMetadata()
                .setLastModified(date.getTime())
                .setCreator("hzx")
                .setDescription("A node network");
        gexf.setVisualization(true);

        Graph graph = gexf.getGraph();
        graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);

        AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
        graph.getAttributeLists().add(attrList);

        Attribute attUrl = attrList.createAttribute("class", AttributeType.INTEGER, "Class");
        Attribute attIndegree = attrList.createAttribute("pageranks", AttributeType.DOUBLE, "PageRank");


        Node gephi = graph.createNode("0");
        gephi
                .setLabel("郝大通")
                .getAttributeValues()
                .addValue(attUrl, "3")
                .addValue(attIndegree, "0.14658");


        Node webatlas = graph.createNode("1");
        webatlas
                .setLabel("郝大通")
                .getAttributeValues()
                .addValue(attUrl, "3")
                .addValue(attIndegree, "0.14658");

        gephi.connectTo("0", webatlas).setWeight(0.8f);

        StaxGraphWriter graphWriter = new StaxGraphWriter();
        File f = new File("test.gexf");
        Writer out;
        try {
            out = new FileWriter(f, false);
            graphWriter.writeToStream(gexf, out, "UTF-8");
            System.out.println(f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
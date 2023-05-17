package app;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import person.Person;

import javax.management.StringValueExp;
import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class XmlWorker implements Reader{

    List<String> outLines = new ArrayList<>();
    int now_line = 0;
    public int amount;
    public XmlWorker(String name){
        filename = name;
    }
    String filename;
    Document document = null;

    public void prepare(){
        try{
            XMLInputFactory input = XMLInputFactory.newInstance();
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newNSInstance().newDocumentBuilder();

            document = documentBuilder.parse(filename);

        }catch (ParserConfigurationException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean nodeTrier(Node n){
        return (n.getNodeType() != Node.TEXT_NODE);
    }
//  Для парсинга полей-классов
    public void getSomthingBig(Node n){
        NodeList pols = n.getChildNodes();
        for (int i = 0; i < pols.getLength(); i++){
            Node pol = pols.item(i);
            if (pol.getNodeType() != Node.TEXT_NODE){
                outLines.add(pol.getChildNodes().item(0).getTextContent());
            }
        }
    }

    public void parse(){
        prepare();
        Node root = document.getDocumentElement();

        NodeList persons = root.getChildNodes();

        for (int i = 0; i < persons.getLength(); i++) {

            Node person = persons.item(i);
            if (nodeTrier(person)) {
                NodeList personProperties = person.getChildNodes();

                for (int j = 0; j < personProperties.getLength(); j++) {
                    Node personProperty = personProperties.item(j);

                    if (personProperty.getNodeName().equals("Location")) {
                        getSomthingBig(personProperty);
                    } else if (personProperty.getNodeName().equals("Coordinates")) {
                        getSomthingBig(personProperty);
                    } else {
                        if (nodeTrier(personProperty)) {
                            outLines.add(personProperty.getChildNodes().item(0).getTextContent());
                        }
                    }
                }
            }
        }
    }



    public void addNewPerson(Person p)throws TransformerFactoryConfigurationError, DOMException {
        Node root = document.getDocumentElement();

        Element person = document.createElement("Person");
        Element id = document.createElement("id");
        Element name = document.createElement("name");
        Element coordinates = document.createElement("Coordinates");
        Element creationTime = document.createElement("CreationDate");
        Element height = document.createElement("height");
        Element birthday = document.createElement("birthday");
        Element eyecolor = document.createElement("EyeColor");
        Element haircolor = document.createElement("HairColor");
        Element location = document.createElement("Location");

        Element coordinates_x = document.createElement("coordinates_x");
        Element coordinates_y = document.createElement("coordinates_y");

        Element location_x = document.createElement("location_x");
        Element location_y = document.createElement("location_y");
        Element location_z = document.createElement("location_z");

        id.setTextContent(String.valueOf(p.getId()));
        name.setTextContent(p.getName());
        creationTime.setTextContent(String.valueOf(p.getCreationDate()));
        height.setTextContent(String.valueOf(p.getHeight()));
        birthday.setTextContent(String.valueOf(p.getBirthday()));
        eyecolor.setTextContent(String.valueOf(p.getEyeColor()));
        haircolor.setTextContent(String.valueOf(p.getHairColor()));

        coordinates_x.setTextContent(String.valueOf(p.getCoordinates().getX()));
        coordinates_y.setTextContent(String.valueOf(p.getCoordinates().getY()));

        location_x.setTextContent(String.valueOf(p.getLocation().getX()));
        location_y.setTextContent(String.valueOf(p.getLocation().getY()));
        location_z.setTextContent(String.valueOf(p.getLocation().getZ()));

        coordinates.appendChild(coordinates_x);
        coordinates.appendChild(coordinates_y);

        location.appendChild(location_x);
        location.appendChild(location_y);
        location.appendChild(location_z);

        person.appendChild(id);
        person.appendChild(name);
        person.appendChild(coordinates);
        person.appendChild(creationTime);
        person.appendChild(height);
        person.appendChild(birthday);
        person.appendChild(eyecolor);
        person.appendChild(haircolor);
        person.appendChild(location);

        root.appendChild(person);
    }


    public void save(TreeSet<Person> set) throws IOException {
        int l = set.size();
        for (int i = 0; i< 10; i++){
            ClearDOM(document);
        }
        for (Person p: set){
            addNewPerson(p);
        }
        writeDocument(document);
    }

    public void writeDocument(Document document) throws TransformerFactoryConfigurationError{
        try{
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            System.out.println(source);
            BufferedWriter bf = new BufferedWriter (new FileWriter("Collection.xml"));
            StreamResult result = new StreamResult(bf);
            tr.transform(source, result);
        }catch (TransformerException | IOException e){
            e.printStackTrace(System.out);
        }
    }
    public void ClearDOM(Document document) throws IOException  {
        Node root = document.getDocumentElement();
//        System.out.println(root);
//        for(int i = 0; i< root.getChildNodes().getLength(); i++){
//            System.out.println(root.getChildNodes().item(i));
//        }
//        System.out.println("_______________________________________");
//        System.out.println(root.getChildNodes().getLength());
        for(int i = 0; i < root.getChildNodes().getLength(); i++){
            if (root.hasChildNodes()){
                root.removeChild(root.getChildNodes().item(i));
            }
        }
//        System.out.println(root);
//        for(int i = 0; i< root.getChildNodes().getLength(); i++){
//            System.out.println(root.getChildNodes().item(i));
//        }
    }

    public void HowMany(){
        amount = (int)(outLines.size()/12);
    }
    @Override
    public boolean Work() {
        if (outLines.size() - now_line > 1) {
            return true;
        }
        return false;
    }
    @Override
    public String WaitData() {
        now_line += 1;
        return outLines.get(now_line - 1);
    }
    public Document getDocument() {
        return document;
    }
}

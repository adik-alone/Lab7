package app;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import person.Location;
import person.Person;

import javax.management.StringValueExp;
import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.IOException;

public class XmlWorker {

    public XmlWorker(String name){
        filename = name;
    }
    String filename;

    public Document prepare(){
        try{
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newNSInstance().newDocumentBuilder();

            Document document = documentBuilder.parse(filename);

            return document;

        }catch (ParserConfigurationException e) {
            e.printStackTrace(System.out);
            return null;
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
    public static void getSomthingBig(Node n){
        NodeList pols = n.getChildNodes();
        for (int i = 0; i < pols.getLength(); i++){
            Node pol = pols.item(i);
            if (pol.getNodeType() != Node.TEXT_NODE){
                System.out.println(pol.getNodeName() + ":" + pol.getChildNodes().item(0).getTextContent());
            }
        }
    }

    public void parse(){
        Document document = prepare();
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
                            System.out.println(personProperty.getNodeName() + ":" + personProperty.getChildNodes().item(0).getTextContent());
                        }
                    }
                }
            }
        }
    }



    public void addNewPerson(Person p)throws TransformerFactoryConfigurationError, DOMException {
        Document document = prepare();
        Node root = document.getDocumentElement();

        Element person = document.createElement("Person");
        Element id = document.createElement("id");
        Element name = document.createElement("name");
        Element coordinates = document.createElement("Coordinates");
        Element creationTime = document.createElement("CreationDate");
        Element height = document.createElement("height");
        Element birthday = document.createElement("birthday");
        Element eyecolor = document.createElement("EyeColor");
        Element haircolor = document.createElement("HfirColor");
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

}

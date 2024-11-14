package ryTracer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import materials.ChromeFactory;
import materials.DiffuseChromeFactory;
import materials.GlassFactory;
import materials.LambertFactory;
import materials.Material;
import materials.MaterialFactoryDepot;
import materials.NormalMapFactory;
import materials.PhongFactory;
import meshes.BoxFactory;
import meshes.Mesh;
import meshes.ObjMeshFactory;
import meshes.PlaneFactory;
import meshes.SphereFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import texture.BitmapTextureFactory;
import texture.CheckerBoardFactory;
import texture.Texture;
import texture.UVMapper;
import texture.UVMapperFactory;
import texture.UVTextureFactory;
import texture.WoodGrainFactory;

import Tracer.Renderer;

public class Scene {

	//lists of crap
	static ArrayList<Mesh> mObjectList = new ArrayList<Mesh>();
	static ArrayList<Camera> cObjectList = new ArrayList<Camera>();
	static ArrayList<Light> lObjectList = new ArrayList<Light>();
	static ArrayList<Material> matObjectList = new ArrayList<Material>();
	static ArrayList<Texture> texObjectList = new ArrayList<Texture>();
	
	//Depot of crap
	MeshFactoryDepot depot = new MeshFactoryDepot();
	LightFactoryDepot l_depot = new LightFactoryDepot();
	CameraFactoryDepot c_depot = new CameraFactoryDepot();
	MaterialFactoryDepot m_depot = new MaterialFactoryDepot();
	TextureFactoryDepot t_depot = new TextureFactoryDepot();
	
	//file crap
	Document loadFile;
	PrintWriter out = null;
	// static String fname = "ball_lineup.xml";
	static String fname = "refracting_ball.xml";
	static String scene_name;
	static int sample_rate;
	static int render_w;
	static int render_h;
	static double aspect;
	static String bname;
	
	public static void main(String[] args) {
		Scene myScene = new Scene();
		if (args.length==1){
			fname = args[0];
		}
		try {
			myScene.loadScene(fname);
			Renderer r = new Renderer();
			Date start = new Date();
			System.out.println("Scene: " + scene_name + " sample rate: " + sample_rate);
			r.render(render_w, render_h, aspect,bname ,  cObjectList.get(0), sample_rate);
			Date end = new Date();
			System.out.println("Completion time = " + (end.getTime() - start.getTime())/1000.0 + " second/s");
		} catch (Exception e) {
			e.printStackTrace();
		}
		myScene.printSceneTypes();
		try{
		myScene.saveScene(fname +".bak" );
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public void loadScene(String fileName) throws Exception{
		depot.registerFactoryDepot(new SphereFactory());
		depot.registerFactoryDepot(new PlaneFactory());
		depot.registerFactoryDepot(new ObjMeshFactory());
		depot.registerFactoryDepot(new BoxFactory());
		l_depot.registerFactoryDepot(new PointLightFactory());
		l_depot.registerFactoryDepot(new DirectionalFactory());
		c_depot.registerFactoryDepot(new PerspectiveCameraFactory());
		m_depot.registerFactoryDepot(new PhongFactory());
		m_depot.registerFactoryDepot(new LambertFactory());
		m_depot.registerFactoryDepot(new ChromeFactory());
		m_depot.registerFactoryDepot(new GlassFactory());
		m_depot.registerFactoryDepot(new DiffuseChromeFactory());
		m_depot.registerFactoryDepot(new NormalMapFactory());
		t_depot.registerFactoryDepot(new UVMapperFactory());
		t_depot.registerFactoryDepot(new UVTextureFactory());
		t_depot.registerFactoryDepot(new WoodGrainFactory());
		t_depot.registerFactoryDepot(new CheckerBoardFactory());
		t_depot.registerFactoryDepot(new BitmapTextureFactory());
		DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
		try{
			//XMLHandler handler = new XMLHandler(mObjectList, depot);
			//xmlBuilder.parse(new InputSource(new FileReader(fileName)), handler);
			//buffered reader stuff
			loadFile = xmlBuilder.parse(new File(fileName));
			Node elementNode = loadFile.getDocumentElement().getFirstChild();
			scene_name = loadFile.getDocumentElement().getAttribute("id");
			bname = loadFile.getDocumentElement().getAttribute("bitmap_file");
			render_w = Integer.valueOf(loadFile.getDocumentElement().getAttribute("render_w"));
			sample_rate = Integer.valueOf(loadFile.getDocumentElement().getAttribute("samples"));
			render_h = Integer.valueOf(loadFile.getDocumentElement().getAttribute("render_h"));
			aspect = Double.valueOf(loadFile.getDocumentElement().getAttribute("render_asp"));
			while(elementNode != null){
				if(elementNode.getNodeName().equalsIgnoreCase("create_mesh")){
					Node createMesh = elementNode.getFirstChild();
					while(createMesh != null){
						String meshName = createMesh.getTextContent().trim();
						if(meshName != null) {
							HashMap<String,String> args = new HashMap<String,String>();
							if(createMesh.hasAttributes()){
								for(int i = 0; i < createMesh.getAttributes().getLength(); i++){
									args.put(createMesh.getAttributes().item(i).getNodeName(), createMesh.getAttributes().item(i).getNodeValue());
								}
							Mesh newMesh = depot.createObject(meshName, args);
							if(args.containsKey("uvMapper")){
								newMesh.setMap((UVMapper) t_depot.createObject(args.get("uvMapper"), args));
								}
							mObjectList.add(newMesh);
							}
						}
						createMesh = createMesh.getNextSibling();
					}
					
				}
				if(elementNode.getNodeName().equalsIgnoreCase("create_camera")){
					Node createMesh = elementNode.getFirstChild();
					while(createMesh != null){
						String meshName = createMesh.getTextContent().trim();
						if(meshName != null) {
							HashMap<String,String> args = new HashMap<String,String>();
							if(createMesh.hasAttributes()){
								for(int i = 0; i < createMesh.getAttributes().getLength(); i++){
									args.put(createMesh.getAttributes().item(i).getNodeName(), createMesh.getAttributes().item(i).getNodeValue());
								}
							cObjectList.add(c_depot.createObject(meshName, args));
							}
						}
						createMesh = createMesh.getNextSibling();
					}
					
				}
				if(elementNode.getNodeName().equalsIgnoreCase("create_light")){
					Node createMesh = elementNode.getFirstChild();
					while(createMesh != null){
						String meshName = createMesh.getTextContent().trim();
						if(meshName != null) {
							HashMap<String,String> args = new HashMap<String,String>();
							if(createMesh.hasAttributes()){
								for(int i = 0; i < createMesh.getAttributes().getLength(); i++){
									args.put(createMesh.getAttributes().item(i).getNodeName(), createMesh.getAttributes().item(i).getNodeValue());
								}
								Light newLight =l_depot.createObject(meshName, args);
								if(args.containsKey("falloff")){
									newLight.setFalloff(args.get("falloff"));
									}
							lObjectList.add(newLight);
							}
						}
						createMesh = createMesh.getNextSibling();
					}
					
				}
				if(elementNode.getNodeName().equalsIgnoreCase("create_mat")){
					Node createMesh = elementNode.getFirstChild();
					while(createMesh != null){
						String meshName = createMesh.getTextContent().trim();
						if(meshName != null) {
							HashMap<String,String> args = new HashMap<String,String>();
							if(createMesh.hasAttributes()){
								for(int i = 0; i < createMesh.getAttributes().getLength(); i++){
									args.put(createMesh.getAttributes().item(i).getNodeName(), createMesh.getAttributes().item(i).getNodeValue());
								}
							Material newMat = m_depot.createObject(meshName, args);
							if(args.containsKey("diffuseTex")){
									Node textureNode = createMesh.getFirstChild();
									while(textureNode != null){
										if(textureNode.getNodeName().equalsIgnoreCase("diffuseTex")){
											HashMap<String,String> args2 = new HashMap<String,String>();
											if(textureNode.hasAttributes()){
												for(int i = 0; i < textureNode.getAttributes().getLength(); i++){
													args2.put(textureNode.getAttributes().item(i).getNodeName(), textureNode.getAttributes().item(i).getNodeValue());
												}
												newMat.setDiffuse(t_depot.createObject(args2.get("type"), args2));
											}
										}
										textureNode = textureNode.getNextSibling();
									}
									
							}
							matObjectList.add(newMat);
							}
						}
						createMesh = createMesh.getNextSibling();
					}
					
				}
				elementNode = elementNode.getNextSibling();
			}
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
			throw e;
		}
		catch (IOException e){
			e.printStackTrace();
			throw e;
		}
	}
	public void saveScene(String fileName) throws Exception{
		try{
			writeOutXml(loadFile, fileName);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void writeOutXml(Document doc , String fileName) {
		try{
			
			out = new PrintWriter(fileName);
			writeNode(doc);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void writeNode(Node node) {
		if(node==null){return;}
		int type = node.getNodeType();
		switch(type){
		case Node.DOCUMENT_NODE:{
			out.println("<?xml version=\"1.0\"?>");
			writeNode(((Document)node).getDocumentElement());
			out.flush();
			break;
			}
		case Node.ELEMENT_NODE: {
			out.print("<");
			out.print(node.getNodeName());
			if(node.hasAttributes()){
				for(int i = 0; i < node.getAttributes().getLength(); i++){
					out.print(" " + node.getAttributes().item(i).getNodeName() + "=\"");
					out.print(node.getAttributes().item(i).getNodeValue()+"\"");
					}
				}
			out.print(">");
			NodeList children = node.getChildNodes();
			if(children != null){
				for(int i = 0; i < children.getLength(); i++){
					writeNode(children.item(i));
					}
				}
			break;
		}
		case Node.ENTITY_REFERENCE_NODE: {
			out.print("&" + node.getNodeName() + ";");
			break;
		}
		case Node.CDATA_SECTION_NODE:
		case Node.TEXT_NODE: {
			out.print(node.getNodeValue());
		}
		}
		if(type == Node.ELEMENT_NODE){
			out.println("</" + node.getNodeName() + ">");
		}
	}

	public void printSceneTypes(){
		for(Mesh meshes : mObjectList){
			meshes.printType();
		}
		for(Camera cameras : cObjectList){
			cameras.printType();
		}
		for(Light lights : lObjectList){
			lights.printType();
		}
		for(Material mats : matObjectList){
			mats.printType();
		}
	}
	
	public void traverseTree(String indent, Element element){
	}

	public static ArrayList<Mesh> getObjects() {
		return mObjectList;
	}
	
	public static ArrayList<Light> getLights() {
		return lObjectList;
	}
	public static ArrayList<Material> getMats() {
		return matObjectList;
	}

}

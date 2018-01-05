package iotdomotics.main;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.hpl.jena.reasoner.Reasoner;

import iotdomotics.ontology.LinkOntology;
import iotdomotics.ontology.ManagerOntology;
import iotdomotics.ontology.OntologyInterface;
import iotdomotics.util.Measure;

@WebServlet(name="MainServlet", urlPatterns={"/MainServlet/measure"})
public class MainServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private LinkOntology link = null;
	private Reasoner pellet = null;
	private OntologyInterface ontology = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		link = new LinkOntology("E:\\WorkSpace\\IoTDomoticsUltimate\\IoTDomotics.owl", "http://www.semanticweb.org/gurui/ontologies/2017/9/IoTDomotics");
		pellet = PelletReasonerFactory.theInstance().create();
		ontology = new ManagerOntology(link, pellet);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getServletPath().equals("/MainServlet/measure")){
			
			Measure measure = new Measure();
			for(String key: request.getParameterMap().keySet()){
				try {
					System.out.println(key + ": " + request.getParameterMap().get(key)[0]);
					this.setFields(measure, key, request.getParameterMap().get(key)[0]);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int random = (int)((Math.random() * 100000) + 1);
			ontology.createIndividual(link.getModel(), random + "-ind", measure);
			ontology.startReasoning();
			String value = ontology.interviewOntActuatorCmd(random + "-ind");
			//value ha sia il value che il tipo e quindi lo isolo
			System.out.println(value);
			response.getWriter().print(value.substring(0, value.indexOf('^')));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getServletPath().equals("/MainServlet/measure")){

			String jb = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

			ObjectMapper objM = new ObjectMapper();
			
			Measure measure = objM.readValue(jb, Measure.class);

			int random = (int)((Math.random() * 100000) + 1);
			ontology.createIndividual(link.getModel(), random + "-ind", measure);
			ontology.startReasoning();
			String value = ontology.interviewOntActuatorCmd(random + "-ind");
			//value ha sia il value che il tipo e quindi lo isolo
			response.getWriter().print(value.substring(0, value.indexOf('^')));
		}
	}

	private void setFields(Measure m, String key, String value) throws IllegalArgumentException, IllegalAccessException{
		Field[] fields = m.getClass().getDeclaredFields();
		for(Field f: fields){
			if(f.getName().equals(key))
				f.set(m, value);
		}
	}
}
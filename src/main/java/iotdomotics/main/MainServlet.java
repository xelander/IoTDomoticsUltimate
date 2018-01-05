package iotdomotics.main;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import iotdomotics.util.Measure;

@WebServlet(name="MainServlet", urlPatterns={"/MainServlet/measure"})
public class MainServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
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
			ResourceManager.getInstance().getOntology().createIndividual(ResourceManager.getInstance().getLink().getModel(), random + "-ind", measure);
			ResourceManager.getInstance().getOntology().startReasoning();
			String value = ResourceManager.getInstance().getOntology().interviewOntActuatorCmd(random + "-ind");
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
			ResourceManager.getInstance().getOntology().createIndividual(ResourceManager.getInstance().getLink().getModel(), random + "-ind", measure);
			ResourceManager.getInstance().getOntology().startReasoning();
			String value = ResourceManager.getInstance().getOntology().interviewOntActuatorCmd(random + "-ind");
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
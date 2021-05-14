package com.paf10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ItemsAPI
 */
@WebServlet("/ItemsAPI")
public class ItemsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Item itemObj;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ItemsAPI() {
		super();
		itemObj = new Item();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = itemObj.insertItem(
				request.getParameter("itemCode"),
				request.getParameter("itemName"),
				request.getParameter("itemPrice"),
				request.getParameter("itemDesc"));
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paras = getParasMap(request);
		String output = itemObj.updateItem(
				Integer.parseInt(paras.get("hidItemIDSave").toString()),
				paras.get("itemCode"),
				paras.get("itemName"),
				paras.get("itemPrice"),
				paras.get("itemDesc"));
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paras = getParasMap(request);
		String output = itemObj.removeItem(Integer.parseInt(paras.get("itemID").toString()));
		response.getWriter().write(output);
	}

	// Convert request parameters to a Map
	private static Map<String,String> getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
					scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params)
			{
				String[] p = param.split("=");
				map.put(p[0], java.net.URLDecoder.decode(p[1], StandardCharsets.UTF_8.name()));
			}
		}
		catch (Exception e)
		{
		}
		return map;
	}
}
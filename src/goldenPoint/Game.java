package goldenPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Game
 */
@WebServlet("/star")
public class Game extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int numCount = 0;
	private double[] num = new double[3];

	private volatile Object lock = new Object();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Game() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(numCount==3)
			numCount=0;
		synchronized (lock) {
			double t = Double.parseDouble(request.getParameter("number"));
			num[numCount++]=t;
			while (numCount != 3) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			double result = 0;
			for (double i : num) {
				result += i;
			}
			result = result / 3 * 0.618;
			//第一名的数字
			double first=200;
			//最后一名的数字
			double last=result;
			for (double i : num) {
				if(Math.abs(i-result)<Math.abs(first-result)){
					first=i;
					continue;
				}
				if(Math.abs(i-result)>Math.abs(last-result)){
					last=i;
					continue;
				}
			}
			request.setAttribute("result", result);
			request.setAttribute("first", first);
			request.setAttribute("last", last);
			request.getRequestDispatcher("result.jsp").forward(request, response);
			lock.notifyAll();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

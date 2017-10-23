package goldenPoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Game
 */
@WebServlet(urlPatterns = "/star", asyncSupported = true)
public class Game extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private volatile int numCount = 0;
	private double[] num = new double[3];

	// private volatile Object lock = new Object();

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
		double t = Double.parseDouble(request.getParameter("number"));
		num[numCount++] = t;

		long time=System.currentTimeMillis();
		while(numCount!=3){
			Thread.yield();
			//设置请求过期时间为1分钟
			if((System.currentTimeMillis()-time)>60000){
				System.out.println("distroy");
				numCount--;
				return ;
			}
		}
		request.getSession().setAttribute("num", numCount);
		double result = 0;
		for (double i : num) {
			result += i;
		}

		result = result / 3 * 0.618;
		// 第一名的数字
		double first = 200;
		// 最后一名的数字
		double last = result;
		for (double i : num) {
			if (Math.abs(i - result) < Math.abs(first - result)) {
				first = i;
				continue;
			}
			if (Math.abs(i - result) > Math.abs(last - result)) {
				last = i;
				continue;
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("first", first);
		request.setAttribute("last", last);
		request.getRequestDispatcher("result.jsp").forward(request, response);
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

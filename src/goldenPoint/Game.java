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
@WebServlet("/star")
public class Game extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int numCount = 0;
	private int[] num = new int[3];
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
			num[numCount++] = Integer.parseInt(request.getParameter("number"));
			while (numCount != 3) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			double result = 0;
			for (int i : num) {
				result += i;
			}
			result = result / 3 * 0.618;
			request.setAttribute("result", result);
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

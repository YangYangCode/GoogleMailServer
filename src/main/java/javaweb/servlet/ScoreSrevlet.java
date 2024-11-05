package javaweb.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.IntSummaryStatistics;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 分數計算與統計的Servlet
 * 網址: /score?score=100&score=85&score=45
 * 印出: 總分, 頻均, 最高, 最低
 **/

@WebServlet("/score")
public class ScoreSrevlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] scores = req.getParameterValues("score");
		resp.getWriter().print("score: " + Arrays.toString(scores));
		
		int max_num = 0;
		int min_num = 0;
		int total = 0;
		double main_num = 0;
		
		for (String score: scores) {
			int i = Integer.valueOf(score);
			if(i> max_num)
				max_num = i;
			if(i< min_num)
				min_num = i;
			total += i;
		}
		main_num = total/scores.length;
	
		resp.getWriter().println("最高分: " + max_num);
		resp.getWriter().println("最低分: " + max_num);
		resp.getWriter().println("平均分: " + main_num);
		resp.getWriter().println("總分  : " + total);
		
		
		
		
		resp.getWriter().println();
		/* 老師方法 */
		
		// String[] to int[]
		int [] intScore = Arrays.stream(scores).mapToInt(Integer::parseInt).toArray();
		
		//統計資料
		IntSummaryStatistics stat = Arrays.stream(intScore).summaryStatistics();
		resp.getWriter().println("scores: " + Arrays.toString(scores));
		resp.getWriter().println("sum: " + stat.getSum());
		resp.getWriter().println("avg: " + String.format("%.1f", stat.getAverage()));
		resp.getWriter().println("max: " + stat.getMax());
		resp.getWriter().println("min: " + stat.getMin());
		resp.getWriter().println("count: " + stat.getCount());
	}

	
}

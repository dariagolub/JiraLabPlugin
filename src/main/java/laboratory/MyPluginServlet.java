package laboratory;

import com.atlassian.templaterenderer.TemplateRenderer;
import com.google.common.collect.Maps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MyPluginServlet extends HttpServlet {
    private static final String TEMPLATE_PATH = "/laboratory.vm";
    private final TemplateRenderer templateRenderer;

    public MyPluginServlet(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException

    {

        Map<String, Object> context = Maps.newHashMap();

        resp.setContentType("text/html;charset=utf-8");
        templateRenderer.render(TEMPLATE_PATH, context, resp.getWriter());

    }
}

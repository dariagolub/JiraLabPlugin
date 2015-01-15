package laboratory;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.sal.api.transaction.TransactionCallback;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.google.common.base.Preconditions.checkNotNull;

public final class NameServlet extends HttpServlet {
    private final ActiveObjects ao;

    public NameServlet(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter w = null;
        try
        {
            w = res.getWriter();
            final JSONArray ja = new JSONArray();
            ao.executeInTransaction(new TransactionCallback<Void>() // (1)
            {
                @Override
                public Void doInTransaction() {
                    try {

                        for (Name todo : ao.find(Name.class)) // (2)
                        {
                           JSONObject jsonObj = new JSONObject();
                           jsonObj.put("description", todo.getDescription());
                           ja.put(jsonObj);
                        }

                    } catch (JSONException ex){}
                    return null;
                }

            });

            w.write(ja.toString());

        } finally {
            if (w != null) {
                w.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        final String description = req.getParameter("name-input");
        ao.executeInTransaction(new TransactionCallback<Name>()
        {
            @Override
            public Name doInTransaction()
            {
                final Name todo = ao.create(Name.class);
                todo.setDescription(description);
                todo.setComplete(false);
                todo.save();
                return todo;
            }
        });

        res.sendRedirect(req.getContextPath() + "/plugins/servlet/name/list");
    }
}

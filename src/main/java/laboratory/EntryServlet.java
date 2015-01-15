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

public class EntryServlet extends HttpServlet {
    private final ActiveObjects ao2;

    public EntryServlet(ActiveObjects ao2) {
        this.ao2 = checkNotNull(ao2);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter w = null;
        try
        {
            w = res.getWriter();
            final JSONArray ja2 = new JSONArray();
            ao2.executeInTransaction(new TransactionCallback<Void>() // (1)
            {
                @Override
                public Void doInTransaction() {
                    try {

                        for (Entry entry : ao2.find(Entry.class)) // (2)
                        {
                            JSONObject jsonObj2 = new JSONObject();
                            jsonObj2.put("entry_name", entry.getEntry());
                            ja2.put(jsonObj2);
                        }

                    } catch (JSONException ex){}
                    return null;
                }

            });

            w.write(ja2.toString());

        } finally {
            if (w != null) {
                w.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        final String entry_name = req.getParameter("entry-input");
        ao2.executeInTransaction(new TransactionCallback<Entry>()
        {
            @Override
            public Entry doInTransaction()
            {
                final Entry entry = ao2.create(Entry.class);
                entry.setEntry(entry_name);
                entry.setComplete(false);
                entry.save();
                return entry;
            }
        });

        res.sendRedirect(req.getContextPath() + "/plugins/servlet/entry/list");
    }

}

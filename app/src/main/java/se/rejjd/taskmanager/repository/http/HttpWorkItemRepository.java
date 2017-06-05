package se.rejjd.taskmanager.repository.http;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import se.rejjd.taskmanager.http.GetTask;
import se.rejjd.taskmanager.http.HttpHelper;
import se.rejjd.taskmanager.http.HttpHelperCommand;
import se.rejjd.taskmanager.http.HttpResponse;
import se.rejjd.taskmanager.model.WorkItem;
import se.rejjd.taskmanager.repository.WorkItemRepository;

public class HttpWorkItemRepository extends HttpHelper {

    private final String URL = "http://10.0.2.2:8080/";

    public void getWorkItems(GetTask.OnResultListener listener) {

        try {
            new GetTask(new HttpHelperCommand() {
                @Override
                public HttpResponse execute() {
                    return get(URL + "workitems");
                }
            },listener).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getWorkItemsFromTeam(final long teamId, GetTask.OnResultListener listener) {

        try {
            new GetTask(new HttpHelperCommand() {
                @Override
                public HttpResponse execute() {
                    return get(URL + "teams/" + teamId + "/workitems");
                }
            },listener).execute();
//            return parserWorkItems(httpResponse.getResponseAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<WorkItem> getWorkItemsByUser(String userId) {
        return null;
    }


    public void getWorkItem(final String id, GetTask.OnResultListener listener) {

        try {
            new GetTask(new HttpHelperCommand() {
                @Override
                public HttpResponse execute() {
                    return get(URL + "workitems/" + id);
                }
            },listener).execute();
//            return parserWorkItem(httpResponse.getResponseAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addWorkItem(WorkItem workItem, GetTask.OnResultListener listener) {

        final String body =
                "{" +
                        "\"id\": " + workItem.getId() + "," +
                        "\"title\": \"" + workItem.getTitle() + "\"," +
                        "\"description\": \"" + workItem.getDescription() + "\"" +
                        "}";

        try {
            new GetTask(new HttpHelperCommand() {
                @Override
                public HttpResponse execute() {
                    return post(URL + "workitems", body);
                }
            },listener).execute();

//            if (httpResponse.getStatusCode() == 201) {
//                String[] splitArray = httpResponse.getHeaders().get("Location").get(0).split("/")
//                        ;
//                String returnValue = splitArray[splitArray.length - 1];
//                return Long.valueOf(returnValue);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method update only the workItems status
    public void updateWorkItem(final WorkItem workItem, GetTask.OnResultListener listener) {

        final String body =
                "{" +
                        "\"id\": " + workItem.getId() + "," +
                        "\"title\": \"" + workItem.getTitle() + "\"," +
                        "\"description\": \"" + workItem.getDescription() + "\"," +
                        "\"status\": \"" + workItem.getStatus() + "\"," +
                        "\"user\": null,"+
                        "\"dateOfCompletion\": null"+
                "}";

        Log.d("johanHttpWorkRepRow127",body);

        try {
            new GetTask(new HttpHelperCommand() {
                @Override
                public HttpResponse execute() {
                    return put(URL + "workitems/" + workItem.getId() , body);
                }
            },listener).execute();
//            Log.d("johan", "updateWorkItem: " + httpResponse.getStatusCode());
//            return (httpResponse.getStatusCode() == 200)? workItem : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<WorkItem> getWorkItemByStatus(String status) {
        //TODO: getWorkItemsByStatus
        return null;
    }

    public WorkItem parserWorkItem(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            long id = jsonObject.getLong("id");
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("description");
            String status = jsonObject.getString("status");
            long userLongId = 0;

            if(!jsonObject.isNull("user")) {
                JSONObject jsonUserObject = new JSONObject(jsonObject.getString("user"));
                userLongId = jsonUserObject.getLong("id"); //TODO MAKE NICER johan
            }
            WorkItem workItem = new WorkItem(id, title, description, userLongId);
            workItem.setStatus(status);

            return workItem;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<WorkItem> parserWorkItems(String jsonString) {
        try {
            List<WorkItem> workItems = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long id = jsonObject.getLong("id");
                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");
                String status = jsonObject.getString("status");

                long userLongId = 0;

                if(!jsonObject.isNull("user")) {
                    JSONObject jsonUserObject = new JSONObject(jsonObject.getString("user"));
                    userLongId = jsonUserObject.getLong("id"); //TODO MAKE NICER johan
                }
                WorkItem workItem = new WorkItem(id, title, description, userLongId);
                workItem.setStatus(status);

                workItems.add(workItem);
            }

            return workItems;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}


package se.rejjd.taskmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class WorkItem implements Parcelable{

    private long id;
    private String title;
    private String description;
    private String status;
    private long userId;

    public WorkItem(long id, String title, String description, long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    protected WorkItem(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<WorkItem> CREATOR = new Creator<WorkItem>() {
        @Override
        public WorkItem createFromParcel(Parcel in) {
            return new WorkItem(in);
        }

        @Override
        public WorkItem[] newArray(int size) {
            return new WorkItem[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public long getUserId() {
        return userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkItem workItem = (WorkItem) o;

        if (id != workItem.id) return false;
        if (title != null ? !title.equals(workItem.title) : workItem.title != null) return false;
        return description != null ? description.equals(workItem.description) : workItem.description == null;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "WorkItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}

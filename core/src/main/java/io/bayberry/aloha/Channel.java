package io.bayberry.aloha;

public class Channel {

    public static Channel UNKNOWN = new Channel();

    private String name;

    public static Channel valueOf(String name) {
        return new Channel(name);
    }

    public Channel(String name) {
        this.name = name;
    }

    private Channel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isResolved() {
        return name != null && name.trim().length() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Channel channel = (Channel) o;

        return name != null ? name.equals(channel.name) : channel.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

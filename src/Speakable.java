public interface Speakable {
    public void giveSpeakPower();

    public void takeAwaySpeakPower();

    public void saySome(String speech) throws EssenceSpeakException;
}

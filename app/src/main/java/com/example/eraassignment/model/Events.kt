sealed class Events {
    data class ShowToast(val message: String) : Events()
}
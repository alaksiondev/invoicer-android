package features.invoice.presentation.screens.create

class CreateInvoiceManager {
    var senderCompanyName: String = ""
    var senderCompanyAddress: String = ""

    var recipientCompanyName: String = ""
    var recipientCompanyAddress: String = ""

    fun clear() {
        senderCompanyAddress = ""
        senderCompanyName = ""
        recipientCompanyName = ""
        recipientCompanyAddress = ""
    }
}
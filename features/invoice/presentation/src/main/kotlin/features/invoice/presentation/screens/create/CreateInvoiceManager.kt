package features.invoice.presentation.screens.create

class CreateInvoiceManager {
    var senderCompanyName: String = ""
    var senderCompanyAddress: String = ""

    fun clear() {
        senderCompanyAddress = ""
        senderCompanyName = ""
    }
}
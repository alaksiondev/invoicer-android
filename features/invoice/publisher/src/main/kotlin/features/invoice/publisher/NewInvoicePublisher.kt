package features.invoice.publisher

import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher

class NewInvoicePublisher : foundation.ui.events.EventAware<Unit> by foundation.ui.events.EventPublisher()
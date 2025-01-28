package features.invoice.publisher

import foundation.events.EventAware
import foundation.events.EventPublisher

class NewInvoicePublisher : EventAware<Unit> by EventPublisher()
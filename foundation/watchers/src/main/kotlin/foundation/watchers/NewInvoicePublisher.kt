package foundation.watchers

import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher

class NewInvoicePublisher : EventAware<Unit> by EventPublisher()
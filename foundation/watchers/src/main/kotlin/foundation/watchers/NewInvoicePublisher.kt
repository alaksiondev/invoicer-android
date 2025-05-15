package foundation.watchers

import io.github.alaksion.invoicer.foundation.ui.events.EventAware
import io.github.alaksion.invoicer.foundation.ui.events.EventPublisher

class NewInvoicePublisher : EventAware<Unit> by EventPublisher()

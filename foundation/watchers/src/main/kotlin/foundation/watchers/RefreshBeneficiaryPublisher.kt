package foundation.watchers

import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher

class RefreshBeneficiaryPublisher : EventAware<Unit> by EventPublisher()

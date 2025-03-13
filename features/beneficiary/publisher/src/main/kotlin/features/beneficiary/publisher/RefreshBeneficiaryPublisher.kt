package features.beneficiary.publisher

import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher

class RefreshBeneficiaryPublisher : foundation.ui.events.EventAware<Unit> by foundation.ui.events.EventPublisher()
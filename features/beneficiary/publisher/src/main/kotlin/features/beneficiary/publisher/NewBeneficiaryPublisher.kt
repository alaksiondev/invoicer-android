package features.beneficiary.publisher

import foundation.events.EventAware
import foundation.events.EventPublisher

class NewBeneficiaryPublisher : EventAware<Unit> by EventPublisher()
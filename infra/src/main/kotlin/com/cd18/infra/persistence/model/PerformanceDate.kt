import com.cd18.infra.persistence.config.model.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Date

@Entity
@Table(name = "performance_date")
class PerformanceDate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "perf_id")
    var performanceInfoId: Long,
    @Column(name = "perf_desc")
    var performanceTime: Date,
) : BaseTimeEntity()
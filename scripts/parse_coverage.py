import xml.etree.ElementTree as ET
import sys
from datetime import datetime

def compare_files(
        branchFile,
        mainFile,
):
    branchCoverage = parse_file(branchFile)
    mainCoverage = parse_file(mainFile)

    generate_markdown_report(branchCoverage, mainCoverage)

def parse_file(xml_file):
    tree = ET.parse(xml_file)
    root = tree.getroot()

    counters = root.findall("counter")

    result = {}

    for count in counters:
        type_ = count.get("type")

        if type_ == "LINE" or type_ == "BRANCH":
            missed = int(count.get("missed"))
            covered = int(count.get("covered"))
            total = missed + covered
            percent = round((covered / total * 100), 2) if total > 0 else 0.0
            result[type_] = {
                "total": total,
                "missed": missed,
                "covered": covered,
                "coverage": percent
            }
    return result

def generate_markdown_report(branch_metrics, main_metrics):
    """Generate a Markdown report comparing coverage metrics."""

    branch_lines = branch_metrics["LINE"]["coverage"]
    brach_branches = branch_metrics["BRANCH"]["coverage"]

    main_lines = main_metrics["LINE"]["coverage"]
    main_branches = main_metrics["BRANCH"]["coverage"]

    # Determine status icons
    line_status = "✅" if branch_lines >= 80 else "❌"
    branch_status = "✅" if brach_branches >= 80 else "❌"

     # Build Markdown report
    report = f"""
## 📊 Test Coverage Report

### Comparison Summary
| Metric | Branch | Main | Difference | Status |
|--------|--------|------|------------|--------|
| Line Coverage | {branch_lines}% | {main_lines}% | {round((main_lines - branch_lines), 2) * -1}% | {line_status} |
| Branch Coverage | {brach_branches}% | {main_branches}% | {round((main_branches - brach_branches), 2) * -1}% | {branch_status} |

_Generated on: {datetime.now().strftime("%Y-%m-%d %H:%M:%S")}_
"""

    print(report)

if __name__ == "__main__":
    compare_files(sys.argv[1], sys.argv[2])
{{/*
Generate a simple name (chart name)
*/}}
{{- define "self-monitor.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Generate a full name (release + chart)
*/}}
{{- define "self-monitor.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "self-monitor.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end }}

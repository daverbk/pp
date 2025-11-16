{{/* simple name */}}
{{- define "self-monitor.name" -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/* fullname = same as name */}}
{{- define "self-monitor.fullname" -}}
{{- include "self-monitor.name" . -}}
{{- end }}
